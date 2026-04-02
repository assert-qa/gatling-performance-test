# Gatling Playbook

## Project Structure
- Use the modular 5-block pattern: Protocol, Feeders, Scenario, Injection, Assertions.
- Organize code by separating config, requests, scenarios, and simulations (see reference/design-patterns.md).
- For JS/TS: place simulation files in `src/` with `.gatling.js`/`.gatling.ts` suffix.
- For JVM: use `src/gatling/java/`, `src/gatling/scala/`, or `src/gatling/kotlin/`.

## Configuration
- JVM: Use Maven or Gradle with correct plugin versions and JVM args for Java 21+.
- JS/TS: Use Node.js 18+, `@gatling.io/cli`, and set `type: module` in package.json.
- Always validate with `scripts/validate.sh` after setup.

## Best Practices
- Always add realistic `pause()` between requests.
- Extract dynamic tokens per user/session.
- Use `circular()` feeders for long tests, `queue()` only for unique data.
- Never use `atOnceUsers` for real load tests; prefer `rampUsers` or `constantUsersPerSec`.
- Always include assertions for pass/fail gates.
- Use `.asJson()` for POST/PUT with JSON bodies (Java/Kotlin/TS).
- For Scala, set contentTypeHeader on the protocol.
- Use `percentile(95)` and `percentile(99)` for response time assertions.

## CI/CD Integration
- Run Gatling in CI with Maven, Gradle, or npx for JS/TS.
- Store HTML reports as build artifacts.
- Fail the build if assertions are breached.

## Advanced Patterns
- Parameterize baseUrl, users, and other settings via environment variables or system properties.
- Use feeders for randomized and property-based test data.
- Use groups and randomSwitch for complex user flows.
- Use `.throttle()` to cap RPS for SLA tests.
- Integrate with cloud CI runners for distributed load.

## Troubleshooting
- Validate project with `scripts/validate.sh` before running.
- Check for missing plugins, wrong file locations, or missing JVM args.
- Review HTML report for failed requests and assertion breaches.
- For JS/TS, ensure simulation files are in `src/` and named correctly.

## Maintenance
- Regularly update Gatling, plugins, and dependencies.
- Refactor simulations as APIs evolve.
- Monitor for new Gatling features and best practices.

---
_Update this playbook as new Gatling features, patterns, or best practices emerge._
