## Simple Friend Manager

### Run

`docker-compose up`

### Technology Stack

Spring Boot, MySQL, JPA, Hibernate, Swagger


### User Stories

1. As a user, I need an API to create a friend connection between two email addresses.

3. As a user, I need an API to retrieve the common friends list between two email addresses.

4. As a user, I need an API to subscribe to updates from an email address.

5. As a user, I need an API to block updates from an email address.

6. As a user, I need an API to retrieve all email addresses that can receive updates from an email address.

### Data Modeling

There are 3 kinds of many-to-many self-reference relationship friend, follower and blocker.
```sql
CREATE TABLE blocker_relationship (
  requestor_email varchar(100) NOT NULL,
  target_email varchar(100) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
);
```
```sql
CREATE TABLE follower_relationship (
  requestor_email varchar(100) NOT NULL,
  target_email varchar(100) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
);
```
```sql
CREATE TABLE friend_relationship (
  requestor_email varchar(100) NOT NULL,
  target_email varchar(100) NOT NULL,
  PRIMARY KEY (requestor_email, target_email)
);
```
### API

http://localhost:8080/swagger-ui.html

Each API for each use case. There are some constraints about the input format, if violated, there will be 400 bad request with error message.

User Story 1: PUT /friends Create a friend connection between two email addresses.
- Constraints: two emails address, valid email format, not the same.
- Add (requestor@example.com, target@example.com) and (target@example.com, requestor@example.com) into the table. Easy for query.  
- If blocked return false.

User Story 2: POST /friends Retrieve the friends list for an email address.
- Constraints: valid email address
- `select target_email from friend_relationship where requestor_email = email`

User Story 3: POST /common-friends Retrieve the common friends list between two email addresses
- Constraints: valid email format, not the same.
- Get intersection of friends list

User Story 4: PUT /followers Subscribe to updates from an email address
- Constraints: valid email format, not the same.
- If requester is blocking target, remove the block.
- Add (requestor@example.com, target@example.com) to the table.

User Story 5: PUT /blockers Block updates from an email address
- Constraints: valid email format, not the same.
- Add (requestor@example.com, target@example.com) to the table.

User Story 6: POST /recipients Retrieve all email addresses that can receive updates from an email address
- Constraints: valid email format, nonnull text;
- Get union of friends, followers and mentioned in the text, and remove all blockers.



