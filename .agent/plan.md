# Project Plan

An Android app named "Splêndora" with two main sections: "Edades" and "Formulas". Use Jetpack Compose, Material 3, Koin, and Room. Focus on character age calculations and wizard activation logic (13 years threshold). The story takes place around the year 1729, so calculations should be based on this era.

## Project Brief

# Project Brief: Splêndora

Splêndora is a character management and magical status tracker set in the year 1729. The app calculates character "activation" milestones based on historical dates rather than contemporary time.

## Features
- **18th-Century Character Management**: Create, edit, and delete character profiles, specifically tracking birth dates within the 1700s era.
- **Era-Specific Age Logic**: Calculates character ages relative to the story's setting in 1729, rather than using the modern system clock.
- **Wizard Activation Engine**: Automatically determines if a character is "activated" (reaches their 13th birthday) according to the year 1729, including a specific logic override for the story's exception character.
- **Temporal Status Simulator**: A tool to verify if a character was "activated" on any specific historical input date (past or future relative to 1729).
- **Historical Adaptive Interface**: A Material 3 responsive layout that uses multi-pane views for character details on large screens and a streamlined list on mobile.

## High-Level Technical Stack
- **Kotlin**: Primary programming language for all logic and UI.
- **Jetpack Compose**: Modern UI toolkit using Material 3 for a vibrant, energetic aesthetic with full edge-to-edge support.
- **Jetpack Navigation 3**: State-driven navigation architecture.
- **Compose Material Adaptive**: Implementation of adaptive layouts for seamless transitions between mobile and large-screen devices.
- **Room Database**: Local persistence for character records and historical data.
- **Koin**: Dependency injection framework for managing application components.
- **Kotlin Coroutines**: Handling asynchronous database operations and age calculations.

## Implementation Steps

### Task_1_Foundation: Set up Room database, Koin for dependency injection, and implement the core Wizard Activation Engine using 1729 as the reference year and implementing character-specific logic overrides.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Koin dependencies added to libs.versions.toml and build.gradle.kts
  - Room database with Character entity (1700s era) and DAO created
  - Wizard Activation logic using 1729 reference implemented
  - Logic override for exception character implemented
  - Project builds successfully
- **StartTime:** 2026-06-06 17:41:59 CEST

### Task_2_Edades_CRUD: Implement the 'Edades' section for 18th-century characters with CRUD operations and an adaptive multi-pane layout.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Character list and detail screens implemented using Compose Adaptive
  - CRUD for characters with birth dates in the 1700s working
  - Adaptive layout (List-Detail) works on different screen sizes
  - Navigation 3 integrated

### Task_3_Temporal_Simulator: Implement the Temporal Status Simulator for historical date verification and the Formulas placeholder screen.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Temporal Status Simulator verifies activation for any historical input date
  - Formulas screen implemented as a placeholder with Material 3 styling
  - Wizard activation logic correctly applied relative to 1729 and historical inputs

### Task_4_Final_Polish: Refine the UI with a vibrant Material 3 theme, Edge-to-Edge display, and an adaptive app icon.
- **Status:** PENDING
- **Acceptance Criteria:**
  - Vibrant Material 3 color scheme implemented (Light/Dark)
  - Full Edge-to-Edge display configured
  - Adaptive app icon matching the magical/historical theme created

### Task_5_Run_Verify: Final verification of the application's stability, 1729 era logic accuracy, and requirement alignment.
- **Status:** PENDING
- **Acceptance Criteria:**
  - App builds and runs without crashes
  - Historical age logic and simulator verified for accuracy
  - Build passes and all existing tests pass
  - Application matches the design aesthetic requirements

