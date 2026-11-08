# Assignment 4 — Dynamic Programming and String Algorithms

## Requirements
- Java 17 or newer
- Maven

## Project structure
- `src/main/java/daa/EditDistance.java` — Recursive, Table and Two Rows versions.
- `src/main/java/daa/StringSearch.java` — Naive, KMP and public `lps()` method.
- `src/main/java/daa/Experiment.java` — fixed-seed experiment, counters and median time.
- `src/test/java/daa/Assignment4Test.java` — JUnit 5 tests.
- `results/results.csv` — experiment data.
- `results/plots/` — experiment charts.
- `REPORT.md` — short report.

## Run
Run tests:
```bash
mvn test
```
Run experiment:
```bash
mvn -q -DskipTests package
java -cp target/classes daa.Experiment
```
The experiment writes `results/results.csv`. The random generator uses seed `42`.

## Git submission
1. Create a GitHub repository and push this project to branch `main`.
2. Create the final tag:
```bash
git add .
git commit -m "feat: complete assignment 4"
git tag v1.0
git push origin main --tags
```
3. Upload `DAA_Assignment4_name_surname_group.zip` to Moodle and add your GitHub URL.

## AI-use acknowledgement
AI assistance was used to help prepare and review the project structure and explanations. The student should review, run, understand and be able to explain the submitted code.
