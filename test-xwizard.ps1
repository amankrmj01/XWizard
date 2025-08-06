# XWizard CLI Test Script
# Run this in PowerShell to test all commands

Write-Host "=== Testing XWizard CLI ===" -ForegroundColor Green

Write-Host "`n1. Testing main command (no args):" -ForegroundColor Yellow
.\gradlew run

Write-Host "`n2. Testing help:" -ForegroundColor Yellow
.\gradlew run --args="--help"

Write-Host "`n3. Testing process command:" -ForegroundColor Yellow
.\gradlew run --args="process --verbose --format cleanup test_file.txt"

Write-Host "`n4. Testing analyze command:" -ForegroundColor Yellow
.\gradlew run --args="analyze --verbose --type detailed test_file.txt"

Write-Host "`n5. Testing convert command:" -ForegroundColor Yellow
.\gradlew run --args="convert --to json --verbose test_file.txt"

Write-Host "`n6. Testing dry-run:" -ForegroundColor Yellow
.\gradlew run --args="process --dry-run --verbose test_file.txt"

Write-Host "`n7. Testing error handling (non-existent file):" -ForegroundColor Yellow
.\gradlew run --args="process nonexistent.txt"

Write-Host "`n=== All tests completed ===" -ForegroundColor Green
