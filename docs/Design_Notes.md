# Design Notes

## Why ArrayList Instead of Array?

Arrays in Java have a **fixed size** set at creation time. LearnTrack stores students, courses, and enrollments dynamically — admins can add or deactivate records at any time during a session.

`ArrayList` solves this by growing automatically as elements are added. It also provides useful methods like `add()`, `remove()`, `isEmpty()`, and `size()` without manual index tracking.

Repositories return defensive copies (`new ArrayList<>(...)`) from `findAll()` so callers cannot modify the internal collection directly.

## Layered Architecture

| Layer | Responsibility |
|-------|----------------|
| `entity` | Data models with encapsulation (private fields, getters/setters) |
| `repository` | In-memory storage (`ArrayList`) — save, find, list |
| `service` | Business rules (validation, enrollment checks, deactivation) |
| `Main` | Menus, user input, wiring repositories into services |
| `exception` | Custom checked exceptions for clear error handling |
| `util` | Reusable helpers (`IdGenerator`, `InputValidator`) |
| `constants` | Menu option numbers and app-wide messages |
| `enums` | Fixed status values (`EnrollmentStatus`, `CourseStatus`) |

`Main.java` does not contain business rules — it delegates to services and catches exceptions to show friendly messages.

## Where Static Members Are Used and Why

| Location | Static member | Reason |
|----------|---------------|--------|
| `IdGenerator` | Counters + `getNextStudentId()` etc. | IDs must be unique across the entire application |
| `IdGenerator` | Private constructor | Utility class — no instances should be created |
| `InputValidator` | Validation methods | Stateless helpers that do not need object state |
| `MenuOptions` | Menu choice constants | Central place for all menu option numbers |
| `AppConstants` | App name, messages | Avoid magic strings scattered in `Main` |

Static is appropriate when behavior or data belongs to the **class as a whole**, not to a single object.

## Enums vs String Constants

- **`EnrollmentStatus`** (`ACTIVE`, `COMPLETED`, `CANCELLED`) — type-safe enrollment states used in `Enrollment` entity and `EnrollmentService`.
- **`CourseStatus`** (`ACTIVE`, `INACTIVE`) — replaces a raw `boolean active` flag on `Course` for clearer modeling.

Enums prevent typos like `"COMPLETD"` and make valid values obvious at compile time.

## Constructor Overloading

`Student` demonstrates constructor overloading with two parameterized constructors — one without email and one with email — so callers can create students flexibly without passing `null` unnecessarily.

## Exception Handling Approach

- **`InvalidInputException`** — bad user input (empty strings, non-numeric IDs).
- **`EntityNotFoundException`** — lookup failures (student/course/enrollment not found).

Both are **checked exceptions**, so callers must handle them explicitly with `try-catch`, reinforcing deliberate error handling in `Main`.
