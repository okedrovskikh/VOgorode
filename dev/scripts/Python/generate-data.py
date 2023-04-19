import json
import random
import re
from argparse import ArgumentParser
from faker import Faker
from faker.providers.address import Provider as AddressProvide
from faker.providers.geo import Provider as GeoProvide
from faker.providers.credit_card import Provider as BankProvide
from faker.providers.internet import Provider as EmailProvider
from faker.providers.phone_number import Provider as PhoneProvider
from faker.providers.person import Provider as PersonProvider
import requests

parser = ArgumentParser(
    prog='VOgorode data generator',
    description='desc'
)
parser.add_argument('-h', default='http://localhost:8080', help='HandymanService address')
parser.add_argument('-l', default='http://localhost:8081', help='LandscapeService address')
parser.add_argument('-r', default='http://localhost:8082', help='RancherService address')

args = parser.parse_args().__dict__

fake = Faker()
address_faker = AddressProvide(fake)
geo_provider = GeoProvide(fake)
bank_provide = BankProvide(fake)
inet_provider = EmailProvider(fake)
phone_provider = PhoneProvider(fake)
person_provider = PersonProvider(fake)

skills = ['plant', 'water', 'sow', 'shovel']

banks = []
payment_systems = ['mastercard', 'visa', 'mir', 'unionpay']

default_photo = ''
with open(file='default-photo.png', mode='r') as file:
    for e in file.readlines():
        default_photo += e
    default_photo = default_photo.encode('base64')


def get_skills():
    res = []
    rand_skills = skills.copy()
    for i in range(random.randint(1, 4)):
        res.append(rand_skills[i])
        rand_skills.remove(rand_skills[i])
    return res


class Account:
    def __init__(self, card_id, payment_system):
        self.card_id = card_id
        self.payment_system = payment_system

    @staticmethod
    def generate():
        return Account(bank_provide.credit_card_number(), random.choice(payment_systems))

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


class User:
    def __init__(self, name, surname, skills, email, telephone, accounts, photo):
        self.name = name
        self.surname = surname
        self.skills = skills
        self.email = email
        self.telephone = telephone
        self.accounts = accounts
        self.photo = photo

    @staticmethod
    def generate(responses):
        accounts = [e['id'] for e in responses]
        return User(person_provider.first_name(), person_provider.last_name(), get_skills(), inet_provider.email(),
                    phone_provider.phone_number(), accounts, default_photo)

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
        point = Point(float(geo_provider.coordinate(latitude)), float(geo_provider.coordinate(longitude)))
        return Field(address_faker.address(), latitude, longitude, point)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


class Point:
    def __init__(self, x1, y1):
        self.x1 = x1
        self.y1 = y1

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
    def generate(responses):
        fields_id = [e['id'] for e in responses]
        return Fielder(person_provider.first_name(), person_provider.last_name(), inet_provider.email(),
                       phone_provider.phone_number(), fields_id)

    def to_json(self):
        return json.dumps(self, default=lambda o: o.__dict__, indent=4)


regex = re.compile(r'')

with open(file='users_data.sql', mode='r') as file:
    while True:
        line = file.readline()
        if line is None:
            break
        if 'handyman' in line:
            email = ''
            telephone = ''
            accounts = [Account.generate() for _ in range(random.randint(1, 4))]
            responses = []
            for e in accounts:
                responses.append(requests.request('post', args['h'] + '/accounts', json=e.to_json()))
            account = User.generate(responses)
            requests.request('post', args['h'] + '/accounts', json=account.to_json())
        if 'rancher' in line:
            fields = [Field.generate() for _ in range(random.randint(0, 3))]
            responses = []
            for e in fields:
                responses.append(requests.request('post', args['r'] + '/fields', json=e.to_json()))
            fielder = Fielder.generate(responses)
            requests.request('post', args['r'] + '/fielders', json=fielder.to_json())
