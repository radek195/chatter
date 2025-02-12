# Real-Time Chat App (Spring Boot + WebSockets)

## Overview

This is a real-time chat application built using Spring Boot with WebSockets.  
Application pairs up two users and allows them to chat.  
Users can leave a chat at any time and get connected with a new user.  
Additionally, system messages are integrated to notify users when someone joins or leaves the conversation.

## Features

**User Pairing:** Automatically pairs two users for a chat session.

**Real-Time Messaging:** Uses WebSockets to enable live communication.

**Dynamic Reconnection:** Users can leave a chat and get paired with a different user.

**System Messaging:** Notifies users when someone enters or leaves the chat.

## Technology Stack

- Framework: Spring Boot

- Communication: WebSockets

- Build Tool: Maven/Gradle

## Setup & Installation

1. Clone the repository:

   `git clone https://github.com/radek195/chatter.git`
2. Navigate to the project directory:

   `cd chatter`
3. Run

   `docker-compose -f docker-compose.yaml up`
4. Create .env file in project root directory with the following variables:
   - `URL=jdbc:postgresql://localhost:5432/postgres`
   - `USER=postgres` 
   - `PASSWORD=password` 
   - `LOCATIONS=src/main/resources/db/migration/`
   - `SCHEMA=chatter`
   - `FLYWAY_PATH=*path to your flyway*`


5. Build and run the application:
    `./gradlew bootRun`
