# Merchant Growth Autopilot

AI-powered merchant growth operations platform.

## Problem

Merchants often have transaction and customer activity data,
but identifying revenue leaks and deciding what action to take
requires continuous manual analysis.

## Solution

Merchant Growth Autopilot uses an agentic workflow to:

Detect → Recommend → Approve → Guardrail → Execute → Measure

## Key Features

- AI-driven growth opportunity detection
- Automated recommendation generation
- Human approval workflow
- Policy guardrails before execution
- Action execution layer
- Business impact measurement
- Agent decision trace
- Merchant dashboard

## Architecture

[architecture diagram]

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- MySQL
- Angular
- Bootstrap
- REST APIs
- Git

## Agent Workflow

Merchant Data

↓

Opportunity Detection

↓

Recommendation

↓

Human Approval

↓

Guardrail Validation

↓

Action Execution

↓

Impact Measurement

## Current MVP

The prototype demonstrates mobile payment conversion
optimization using a controlled sandbox execution flow.

## Running Locally

### Backend

mvn spring-boot:run

### Frontend

cd frontend
npm install
ng serve

Backend: http://localhost:8080

Frontend: http://localhost:4200

Project Status

MVP completed.

# Future Scope

More growth opportunity types
Additional action adapters
Real merchant integrations
Production-grade impact measurement
