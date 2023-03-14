import datetime
import random
import argparse
import zipfile
import warnings

from faker import Faker
from faker.providers.internet import Provider as EmailProvider
from faker.providers.phone_number import Provider as PhoneProvider

des = 'Generate 3.14M users for db table: https://padlet.com/p4bp94qnf6/java-u2whzedsnhf7uv48/wish/2512566608'
parser = argparse.ArgumentParser(
    prog='Users Generator',
    description=des
)
parser.add_argument('--type', default='u_type', help='Value is a name of column with user type')
parser.add_argument('--login', default='u_login', help='Value is a name of login column')
parser.add_argument('--email', default='email', help='Value is a email column name')
parser.add_argument('--telephone', default='telephone', help='value is a phone number column name')
parser.add_argument('--creation-date', default='creation_date', help='Value is a creation date column name')
parser.add_argument('--update-date', default='update_date', help='Value is a update date column name')

args = parser.parse_args().__dict__

creation_date = datetime.datetime.utcnow()
format_str = '(\'{}\', \'{}\', \'{}\', \'{}\', \'{}\', \'{}\')'
type_counter = 0
user_types = ['handyman', 'landscape', 'rancher']
fake = Faker()
email_fake = EmailProvider(fake)
phone_fake = PhoneProvider(fake)


def create_type():
    global type_counter
    rand = random.Random().randint(a=0, b=1)
    if rand == 0:
        if type_counter == 3:
            type_counter += 1
            return user_types[0]
        else:
            type_counter = 0
            return user_types[2]
    else:
        return user_types[1]


def generate_data():
    return format_str.format(create_type(), email_fake.user_name(), email_fake.email(),
                             phone_fake.phone_number(), creation_date, creation_date)


sql_str = 'insert into users ({}, {}, {}, {}, {}, {}) values'.format(args['type'], args['login'], args['email'],
                                                                     args['telephone'], args['creation_date'],
                                                                     args['update_date']) + ' {};\n'

with zipfile.ZipFile(file='users_data.zip', mode='w', compression=zipfile.ZIP_BZIP2) as zip_file:
    with warnings.catch_warnings():
        warnings.simplefilter('ignore')
        for _ in range(3_140_000):
            zip_file.writestr(zinfo_or_arcname='users_data.sql', data=sql_str.format(str(generate_data())))
