# Training Schedule Generator

The Training Schedule Generator is a Java application that takes a CSV file containing participants information and
generates a training schedule by grouping participants based on their timezones and preferences for working hours.

## Overview

The application uses an AI constraint solver, powered by [OptaPlanner](https://www.optaplanner.org/docs/optaplanner/latest/planner-introduction/planner-introduction.html),
to group participants in the least amount of sessions possible, considering their timezones and preferences for acceptable working hour shifts.

The application accepts a CSV file as input, which should contain the following columns:
- **Name:** The name of the participant.
- **Role:** The role of the participant.
- **Region:** The home region of the participant
- **Timezone:** The timezone of the participant specified using only the timezone offset (e.g., "+02:00").
- **Acceptable Hours Shift:** Acceptable working hour shift (e.g. -2).

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Maven (for building the application)

## Getting Started

1. Clone the repository:

```
git clone git@github.com:th3n3rd/training-schedule-generator.git;
```

2. Build the application using Maven:

```
cd training-schedule-generator
mvn clean package
```

3. Run the application:

```
java -jar target/training-schedule-generator.jar ./participants-sample.csv
```

4. The application will generate the training schedule and display the output.

```
Solved training schedule:

From 00:00 to 04:00 UTC
3 Participants: Molly Morris, Maggie Mitchell, Minnie Moore

From 13:00 to 17:00 UTC
4 Participants: Danny Davis, Daisy Donovan, Daphne Drake, Derek Daniels

From 14:00 to 18:00 UTC
6 Participants: Tommy Turner, Timmy Thompson, Dexter Douglas, Toby Taylor, Tina Tyler, Teddy Torres

2 Unplaced: Mickey Miller, Mandy Murphy
```

## Sample CSV Input

[Here](./participants-sample.csv) is an example of how the file should be structured:

Please ensure that the values are properly formatted and separated by commas.

## License

This project is licensed under the [MIT License](./LICENSE).
