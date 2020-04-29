# Siburarenda

Rest сервис по аренде помещений.

## Available URLs
### Public:
* ##### /api/public/login (method = POST)
    * ###### request
            {
                "username" : "username"
                "password" : "password"
            }
    * ###### response
            {
                "firstName": "user",
                "lastName": "user",
                "roles": [
                    "ROLE1",
                    "ROLE2",
                    "ROLE3"
                ],
                "username": "user",
                "token": "token"
            } 
            OR
            String: Invalid username or password
            
* ##### /api/public/register (method = POST)
    * ###### request
            {
                "username" : "username",
                "password" : "password",
                "email" : "email@email.com",
                "firstName" : "user",
                "lastName" : "user",
                "company" : "google"
            }
    * ###### response
            String: User with username: usernsame was registred.
            OR
            String: User with such email or username already exists
* ##### /api/public/rooms (method = GET)
    * ###### response
            [
                {
                    "name": "name1",
                    "auditory": -1,
                    "description": "sometext",
                    "tags": [
                        "tag1",
                        "tag2",
                        "name of room this room references to"
                    ]
                },
                {
                     "name": "name2",
                     "auditory": 123,
                     "description": "lostoftext",
                      "tags": [
                          "tag12",
                          "tag27",
                          "-1 (if no room reference to)"
                      ]
                }
            ]               
### User
* ##### /api/user/order (method = POST)
    * ###### request 
            {
                "name" : "name of event",
                "auditory" : "100",
                "type" : "PARTY", //types can be: MATCH, TRAINING, PARTY, DRINKING_PARTY, OTHER
                "rooms" : [
                    "bigroom",
                    "really big room", 
                    "small room"
                    ],
                "user" : "username",
                //In dates you should escape double quotes and send string
                "dates" :"[
                {\"from\":\"Wed Apr 8 12:00:00 NOVT 2020\",\"to\":\"Wed Apr 8 12:00:00 NOVT 2020\", \"status\" : \"ACTIVE\"},
                {\"from\":\"Thu Apr 9 12:00:00 NOVT 2020\",\"to\":\"Thu Apr 9 12:00:00 NOVT 2020\", \"status\" : \"ACTIVE\"}
                ]", 
                "comment" : "comment"
            }
            
    * ###### response
            {
                "id": 2,
                "created": "2020-04-29T17:57:42.962+0000",
                "updated": "2020-04-29T17:57:42.962+0000",
                "status": "NOT_ACTIVE",
                "name": "name of event",
                "type": "PARTY",
                "description": "comment",
                "auditory": 100,
                "dates": "[
                   {\"from\":\"Wed Apr 8 12:00:00 NOVT 2020\",\"to\":\"Wed Apr 8 12:00:00 NOVT 2020\", \"status\" : \"ACTIVE\"},
                   {\"from\":\"Thu Apr 9 12:00:00 NOVT 2020\",\"to\":\"Thu Apr 9 12:00:00 NOVT 2020\", \"status\" : \"ACTIVE\"}
                   ]" 
                "rooms": []
            }


### Manager
* ##### /api/manage/users/{id} (method = GET)
    * ###### response 
            {
                "id": 1,
                "username": "user",
                "firstName": "user",
                "lastName": "user",
                "email": "user",
                "roles": [
                    "USER",
                    "MANAGER",
                    "ADMIN"
                ]
            }
* ##### /api/manage/rooms/{id} (method = GET) //info about room calendar
    * ###### response 
            [
                {
                    "name": "Party",
                    "auditory": 1234,
                    "type": "PARTY",
                    "rooms": [
                        {
                            "name": "name of room",
                            "auditory": 7120,
                            "description": "description"
                            "tags": [
                                "tag",
                                "tag",
                                "tag"
                            ]
                        }
                    ],
                    "user": {
                        "id": 1,
                        "username": "user",
                        "firstName": "user",
                        "lastName": "user",
                        "email": "user",
                        "roles": [
                            "USER",
                            "MANAGER",
                            "ADMIN"
                        ]
                    },
                    "dates": [
                        {
                            "from": "Mon Apr 13 14:51:17 NOVT 2020",
                            "to": "Mon Apr 13 14:51:17 NOVT 2020",
                            "status": "ACTIVE"
                        },
                        {
                            "from": "Mon Apr 13 14:51:17 NOVT 2020",
                            "to": "Mon Apr 13 14:51:17 NOVT 2020",
                            "status": "NOT_ACTIVE"
                        },
                        {
                            "from": "Mon Apr 13 14:51:17 NOVT 2020",
                            "to": "Mon Apr 13 14:51:17 NOVT 2020",
                            "status": "DELETED"
                        }
                    ],
                    "description": "comment"
                }
            ]
* ##### /api/manage/events (method = GET) //info about all events
    * ###### response
            {Same content as in /api/manage/rooms/{id}}
