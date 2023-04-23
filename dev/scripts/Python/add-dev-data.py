import os.path
import subprocess
import sys
import time

import psycopg2

status_code = 0

download_requirements_skip_flag = False
generate_landscape_accounts_skip_flag = not os.path.exists('users_data.sql')
postgres_skip_flag = False
generate_data_skip_flag = False

requirements = "python -m pip install --user -r requirements.txt"
generate_landscape_accounts = "python generate-landscape-accounts.py"
generate_dev_data = "python generate-dev-data.py"

start = time.thread_time()
print("Start downloading requirements")
if not download_requirements_skip_flag:
    status_code = subprocess.call(requirements, shell=True)
print("Finished downloading requirements")
print(time.thread_time() - start)

if status_code != 0:
    print("Error during downloading requirements")
    sys.exit(status_code)

start = time.thread_time()
print("Start generating accounts")
if generate_landscape_accounts_skip_flag:
    status_code = subprocess.call(generate_landscape_accounts, shell=True)
print("Finished generating accounts")
print(time.thread_time() - start)

if status_code != 0:
    print("Error during generating accounts")
    sys.exit(status_code)

start = time.thread_time()
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
print(time.thread_time() - start)

start = time.thread_time()
print("Start generating data")
if not generate_data_skip_flag:
    status_code = subprocess.call(generate_dev_data, shell=True)
print("Finished generating data")
print(time.thread_time() - start)

if status_code != 0:
    print("Error during generating data")
    sys.exit(status_code)
