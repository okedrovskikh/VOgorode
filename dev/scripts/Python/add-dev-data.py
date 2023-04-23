import os.path
import subprocess
import sys

import psycopg2

status_code = 0

download_requirements_skip_flag = False
generate_landscape_accounts_skip_flag = not os.path.exists('users_data.sql')
postgres_skip_flag = False
generate_data_skip_flag = False

requirements = "python -m pip install --user -r requirements.txt"
generate_landscape_accounts = "python generate-landscape-accounts.py"
generate_test_data = "python generate-data.py"

print("Start downloading requirements")
if not download_requirements_skip_flag:
    status_code = subprocess.call(requirements, shell=True)
print("Finished downloading requirements")

if status_code != 0:
    print("Error during downloading requirements")
    sys.exit(status_code)

print("Start generating accounts")
if generate_landscape_accounts_skip_flag:
    status_code = subprocess.call(generate_landscape_accounts, shell=True)
print("Finished generating accounts")

if status_code != 0:
    print("Error during generating accounts")
    sys.exit(status_code)

print("Start putting value in postgres")

if not postgres_skip_flag:
    conn = psycopg2.connect(dbname='vogorode', user='postgres', password='123', host='localhost', port=5433)

    with open(file='users_data.sql', mode='r') as file:
        with conn.cursor() as cursor:
            while True:
                line = file.readline()
                if not line:
                    break
                cursor.execute(line)
            conn.commit()

print("Finished putting value in postgres")

print("Start generating data")
if not generate_data_skip_flag:
    status_code = subprocess.call(generate_test_data, shell=True)
print("Finished generating data")

if status_code != 0:
    print("Error during generating data")
    sys.exit(status_code)
