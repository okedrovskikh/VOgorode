import subprocess
import sys

status_code = 0

download_requirements_skip_flag = True
generate_landscape_accounts_skip_flag = True
postgres_skip_flag = False

requirements = "python -m pip install -r requirements.txt"
generate_landscape_accounts = "python generate-landscape-accounts.py"
generate_dev_data = "python generate-dev-data.py"

print("Start downloading requirements")
if not download_requirements_skip_flag:
    status_code = subprocess.call(requirements, shell=True)
print("Finished downloading requirements")

if status_code != 0:
    print("Error during downloading requirements")
    sys.exit(status_code)

import psycopg2

print("Start generating accounts")
if not generate_landscape_accounts_skip_flag:
    status_code = subprocess.call(generate_landscape_accounts, shell=True)
print("Finished generating accounts")

if status_code != 0:
    print("Error during generating accounts")
    sys.exit(status_code)

print("Start putting value in postgres")

if not postgres_skip_flag:
    conn = psycopg2.connect(dbname='vogorode', user='postgres', password='123', host='localhost', port=10001)

    with open(file='users_data.sql', mode='r') as file:
        with conn.cursor() as cursor:
            while True:
                line = file.readline()
                if not line:
                    break
                cursor.execute(line)
            conn.commit()

print("Finished putting value in postgres")
