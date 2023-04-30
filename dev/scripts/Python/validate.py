counter = 0

with open(file="users_data.sql", mode='r') as file:
    while True:
        line = file.readline()
        if not line:
            break
        if 'handyman' in line:
            counter += 1

print(counter)
