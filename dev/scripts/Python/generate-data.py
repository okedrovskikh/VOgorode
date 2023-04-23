import base64
import codecs
import json
import random
import re
import argparse
import sys

from faker import Faker
from faker.providers.address import Provider as AddressProvide
from faker.providers.geo import Provider as GeoProvide
from faker.providers.credit_card import Provider as BankProvide
from faker.providers.person import Provider as PersonProvider
import requests

parser = argparse.ArgumentParser(
    prog='VOgorode data generator',
    description='desc'
)
parser.add_argument('--h', default='http://localhost:8080', help='HandymanService address')
parser.add_argument('--l', default='http://localhost:8082', help='LandscapeService address')
parser.add_argument('--r', default='http://localhost:8081', help='RancherService address')

args = parser.parse_args().__dict__

address_faker = AddressProvide(Faker())
geo_provider = GeoProvide(Faker())
bank_provide = BankProvide(Faker())
person_provider = PersonProvider(Faker())

skills = ['plant', 'water', 'sow', 'shovel']

banks = ['Cici Bank', 'Bank of Emerika', 'Kremniy Alley Bank','Chicha construction Bank', 'Bisto credit',
         'Slavebank', 'Bank of Prikolov', 'Royal bank of USCR', 'Sperbank', 'Credit Chicha Bank']
payment_systems = ['mastercard', 'visa', 'mir', 'unionpay']

default_photo = None
with codecs.open(filename='default-photo.jpg', mode='r',
                 encoding='base64') as file:
    default_photo = base64.b64encode(file.read()).decode('ascii')

regex = re.compile(r'\(\'.*\', \'.*\', \'(.*)\', \'(.*)\', \'.*\', \'.*\'\)')


def get_skills():
    return list(set([skills[i] for i in range(random.randint(1, 4))]))


def generate_polygon():
    x1 = geo_provider.coordinate()
    x2 = geo_provider.coordinate()
    y1 = geo_provider.coordinate()
    y2 = geo_provider.coordinate()
    return f'POLYGON (({x1} {y1}, {x1} {y2}, {x2} {y2}, {x2} {y1}, {x1} {y1}))'


def get_email_and_telephone(line):
    split_line = regex.findall(line)[0]
    return split_line[0], split_line[1]


class Account:
    def __init__(self, card_id, payment_system, bank):
        self.card_id = card_id
        self.payment_system = payment_system
        self.bank = bank

    @staticmethod
    def generate():
        return Account(bank_provide.credit_card_number(),
                       random.choice(payment_systems), random.choice(banks))

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


class User:
    def __init__(self, name, surname, skills, email, telephone, accounts,
                 photo):
        self.name = name
        self.surname = surname
        self.skills = skills
        self.email = email
        self.telephone = telephone
        self.accounts_id = accounts
        self.photo = photo

    @staticmethod
    def generate(line, responses):
        email, telephone = get_email_and_telephone(line)
        accounts = [e['id'] for e in responses]
        return User(person_provider.first_name(), person_provider.last_name(),
                    get_skills(),
                    email, telephone, accounts, default_photo)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


class Field:
    def __init__(self, address, latitude, longitude, area):
        self.address = address
        self.latitude = latitude
        self.longitude = longitude
        self.area = area

    @staticmethod
    def generate():
        latitude = float(geo_provider.latitude())
        longitude = float(geo_provider.longitude())
        area = generate_polygon()
        return Field(address_faker.address(), latitude, longitude, area)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


class Fielder:
    def __init__(self, name, surname, email, telephone, fields_id):
        self.name = name
        self.surname = surname
        self.email = email
        self.telephone = telephone
        self.fields_id = fields_id

    @staticmethod
    def generate(line, responses):
        email, telephone = get_email_and_telephone(line)
        fields_id = [e['id'] for e in responses]
        return Fielder(person_provider.first_name(),
                       person_provider.last_name(),
                       email, telephone, fields_id)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


def pool_execute(line):
    if 'handyman' in line:
        accounts = [Account.generate() for _ in range(random.randint(1, 4))]
        responses = []
        for e in accounts:
            response = requests.request('post', args['h'] + '/accounts', headers={'Content-Type': 'application/json'}, data=e.to_json())
            if response.status_code != 200:
                print(response.status_code)
                print(e.to_json())
                print(response.content.decode('utf-8'))
                sys.exit(-1)
            responses.append(response.json())
        user = User.generate(line, responses)
        response = requests.request('post', args['h'] + '/users', headers={'Content-Type': 'application/json'}, data=user.to_json())

        if response.status_code != 200:
            print(response.status_code)
            print(user.to_json())
            print(response.content.decode('utf-8'))
            sys.exit(-1)
    if 'rancher' in line:
        fields = [Field.generate() for _ in range(random.randint(0, 3))]
        responses = []
        for e in fields:
            response = requests.request('post', args['r'] + '/fields',  headers={'Content-Type':'application/json'},data=e.to_json())
            if response.status_code != 200:
                print(response.status_code)
                print(e.to_json())
                print(response.content.decode('utf-8'))
                sys.exit(-1)
            responses.append(response.json())
        fielder = Fielder.generate(line, responses)
        response = requests.request('post', args['r'] + '/fielders', headers={'Content-Type': 'application/json'}, data=fielder.to_json())
        if response.status_code != 200:
            print(response.status_code)
            print(fielder.to_json())
            print(response.content.decode('utf-8'))
            sys.exit(-1)


with open(file='users_data.sql', mode='r') as file:
    while True:
        line = file.readline()
        if not line:
            break
        pool_execute(line)
