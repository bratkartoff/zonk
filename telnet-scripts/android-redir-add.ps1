$writer = New-Object System.IO.StreamWriter((New-Object Net.Sockets.TcpClient -Argumentlist 'localhost',5554).GetStream())
$writer.WriteLine("auth $(Get-Content ~/.emulator_console_auth_token)")
$writer.WriteLine("redir add tcp:20043:20043")
$writer.WriteLine("quit")
$writer.Flush()
