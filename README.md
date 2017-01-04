ADB-Shell-Integration
========

A simple implementation of executing ADB shell commands directly from a Java Application. 

# Usage:

From any main function, execute the following commands,


``` CopyUtils.copyAdbFiles(); ```

This will copy ADB dependencies to a temporary folder


``` ShellUtils.runProcess(true, "cd C:/temp & adb command"); ```

This will move to the temporary directory & execute the give command. 

A working sample could be found on the Main.java class
