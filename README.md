# backend

user;12345 -> USER, MANAGER, ADMIN
/api/public/login POST
REQUEST:
username:
password:
RESPONSE:
username:
token:

token to header: key:Authorization value:Bearer_{token}
FOR EVERYONE: /api/public/** 
/rooms(GET all rooms); 
/register(POST: register new user) 
{
  username:
  password:
  email:
  firstName:
  lastName:
  company:
}

FOR MANAGERS: /api/manage/**
/events (GET all events)
/users/{id}(GET user info by user_id)
/rooms/{id}(GET room calendar with user info)

FOR ADMINS: /api/admin/**
/rooms/** POST:
  /delete
  /add
  /update (requed name:
                  auditory:)
/users
/users/{id}
/register (POST: register new manager)
{
  username:
  password:
  email:
  firstName:
  lastName:
  company:
}
