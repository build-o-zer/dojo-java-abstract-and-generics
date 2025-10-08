# Dojo Java Abstract and Generics

## Quick Start

## Prerequisites

Make sure you have [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/) installed on your machine.

Make sure you can compile and run Java code on your machine :

```bash 
$ mvn compile test
```

## Running the Dojo

Follow Option 1 or Option 2 below to start the Dojo.
Then open your browser and navigate to `http://localhost:9595` and follow the instructions.

### Option 1: Using Docker Compose

Prerequisite: Make sure you have [Docker](https://docs.docker.com/get-docker/) a

```bash
$ docker-compose up --build
```

Then open your browser and navigate to `http://localhost:9595` and follow the instructions.

### Option 2: Running Locally with busybox

Prerequisite: Make sure you have [busybox](https://busybox.net/) installed on your machine.
> Note: to install busybox on Ubuntu, run `sudo apt-get install busybox`


```bash
$ busybox httpd -p 9595 -f -h ./containers/dojo-site/content
```


