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
**Total Duration:** 25m 36s

### Task_1_Foundation: Set up Room database, Koin for dependency injection, and implement the core Wizard Activation Engine using 1729 as the reference year.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - Room, Koin setup complete
  - Activation engine implemented
- **Duration:** 7m 45s

### Task_2_Edades_CRUD: Implement the 'Edades' section for 18th-century characters with CRUD operations and an adaptive multi-pane layout.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - CRUD works
  - Adaptive layout implemented
- **Duration:** 15m 5s

### Task_3_Integrated_Time_Travel: Implement the integrated historical date picker on the Edades screen and the Formulas placeholder.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - Date picker in Edades top bar
  - List updates reactively to date changes
  - Formulas screen exists

### Task_4_Final_Polish: Refine UI with Material 3 theme, Edge-to-Edge, and adaptive app icon.
- **Status:** COMPLETED
- **Acceptance Criteria:**
  - Vibrant M3 theme
  - Edge-to-Edge display
  - Adaptive icon
- **Duration:** 50s

### Task_5_Run_Verify: Final verification of the application's stability, 1729 era logic accuracy, and requirement alignment.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - App builds and runs without crashes
  - Historical age logic verified with integrated date picker
  - Build passes on phone emulator
- **Duration:** 1m 56s

