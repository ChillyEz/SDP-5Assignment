param(
    [Parameter(Mandatory = $true)]
    [string]$PlantUmlJar,
    [string]$JavaExecutable = 'java'
)

$ErrorActionPreference = 'Stop'
$jarPath = (Resolve-Path -LiteralPath $PlantUmlJar).Path
$diagrams = @(
    (Join-Path $PSScriptRoot 'strategy-pattern.puml'),
    (Join-Path $PSScriptRoot 'client-wiring.puml')
)

foreach ($format in @('svg', 'png')) {
    & $JavaExecutable '-Djava.awt.headless=true' -jar $jarPath -charset UTF-8 -failfast2 "-t$format" @diagrams
    if ($LASTEXITCODE -ne 0) {
        throw "PlantUML failed to render $format diagrams (exit code $LASTEXITCODE)."
    }
}

Write-Output "Rendered SVG and PNG diagrams in $PSScriptRoot"
