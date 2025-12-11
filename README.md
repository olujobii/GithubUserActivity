Find project on [roadmap.sh](https://roadmap.sh/projects/github-user-activity)

# GitHub User Activity
A CLI-based application that takes in a github username as inputs and list all events assosicated with the user.

## Feature
- List all activities performed by github user.

## Installation
1. Clone the repo
```bash
git clone https://github.com/olujobii/GithubUserActivity
```

2. Navigate to the root directory of the cloned repository in the terminal
   
3. Perform a maven clean operation in the terminal
```bash
#For Linux, MacOS
./mvnw clean

#For Windows
.\mvnw.cmd clean
```

4. Perform a maven package operation in the terminal
```bash
#For Linux, MacOS
./mvnw package

#For Windows
.\mvnw.cmd package
```

5. Run the program
```bash
java -jar target\GithubUserActivity-1.0.SNAPSHOT.jar <github-username>
#Where github-username is the github user input passed
```
