[Setup]
AppName=JavaWizard
AppVersion=1.0.0
AppPublisher=amankrmj
AppPublisherURL=https://github.com/amankrmj01
DefaultDirName={autopf}\JavaWizard
DefaultGroupName=JavaWizard
OutputDir=../installer
OutputBaseFilename=JavaWizard-Native-Setup-1.0.0
Compression=lzma
SolidCompression=yes
WizardStyle=modern
ArchitecturesAllowed=x64compatible
ArchitecturesInstallIn64BitMode=x64compatible
ChangesEnvironment=yes

[Languages]
Name: "english"; MessagesFile: "compiler:Default.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "addtopath"; Description: "Add JavaWizard to system PATH"; GroupDescription: "System Integration"; Flags: checkedonce

[Files]
; Native executable only - no JVM required!
Source: "../build/native/nativeCompile/javawizard.exe"; DestDir: "{app}\bin"; Flags: ignoreversion

[Icons]
Name: "{group}\JavaWizard"; Filename: "{app}\bin\javawizard.exe"; WorkingDir: "{app}"
Name: "{group}\{cm:UninstallProgram,JavaWizard}"; Filename: "{uninstallexe}"
Name: "{autodesktop}\JavaWizard"; Filename: "{app}\bin\javawizard.exe"; WorkingDir: "{app}"; Tasks: desktopicon

[Registry]
Root: HKLM; Subkey: "SYSTEM\CurrentControlSet\Control\Session Manager\Environment"; ValueType: expandsz; ValueName: "Path"; ValueData: "{olddata};{app}\bin"; Tasks: addtopath; Check: NeedsAddPath('{app}\bin')

[Run]
Filename: "{app}\bin\javawizard.exe"; Parameters: "--help"; Description: "Test JavaWizard installation"; Flags: waituntilterminated postinstall skipifsilent; WorkingDir: "{app}"

[Code]
function NeedsAddPath(Param: string): boolean;
var
  OrigPath: string;
begin
  if not RegQueryStringValue(HKEY_LOCAL_MACHINE,
    'SYSTEM\CurrentControlSet\Control\Session Manager\Environment',
    'Path', OrigPath)
  then begin
    Result := True;
    exit;
  end;
  Result := Pos(';' + Param + ';', ';' + OrigPath + ';') = 0;
end;
