#!/bin/bash
#SBATCH --account=rrg-lelis
#SBATCH --time=01-00:00
#SBATCH --job-name=eval
#SBATCH --cpus-per-task=1
#SBATCH --mem=4G
#SBATCH --output=evals/%x-%j.out

module load java/17.0.2

javac -cp "lib/*:src" -d bin src/ai/synthesis/LocalSearch/Tests/EvaluateLast.java
java -cp "lib/*:bin" ai.synthesis.LocalSearch.Tests.EvaluateLast 5 "$1" "$2"
