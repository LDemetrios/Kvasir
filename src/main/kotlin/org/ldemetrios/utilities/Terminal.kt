package org.ldemetrios.utilities


import java.io.*
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors


class TerminalException(val errorCode: Int, message: String) : RuntimeException(message)

data class ExecResult(
    val output: List<String>,
    val error: List<String>,
    val returnCode: Int
)

fun exec(command: List<String>, input: Reader? = null, root: File? = null, timeout: Long = -1): ExecResult {
    val builder = ProcessBuilder(command)
    if (root != null) builder.directory(root)
    val process = builder.start()

    val processInput = process.outputStream
    if (input != null) {
        OutputStreamWriter(processInput).use { writer ->
            val buffer = CharArray(8192)
            var charsRead: Int
            while ((input.read(buffer).also { charsRead = it }) != -1) {
                writer.write(buffer, 0, charsRead)
            }
            writer.flush()
        }
    }
    process.outputStream.close()

    val processOutput = BufferedReader(InputStreamReader(process.inputStream))
    val processError = BufferedReader(InputStreamReader(process.errorStream))

    val ex = if (timeout < 0) process.waitFor() else {
        process.waitFor(timeout, TimeUnit.MILLISECONDS);
        process.destroy()
        -1
    }

    return ExecResult(
        processOutput.lines().collect(Collectors.toList()),
        processError.lines().collect(Collectors.toList()),
        ex
    )
}

fun perform(command: List<String>): List<String> {
    val (output, error, ret) = exec(command)

    for (e in error) System.err.println(e)

    if (ret != 0) {
        throw TerminalException(
            ret,
            "Command ${toOnelineCommand(command)} failed with return code $ret (Probably means '${errno(ret)}')"
        )
    }

    return output
}

operator fun String.invoke(vararg args: String): List<String> = perform(listOf(this) + args)

fun toOnelineCommand(list: List<String>): String = list.joinToString(" ") {
    if (it.any { it in "'\"\$ <>" }) {
        "'" + it.replace("'", "'\\''") + "'"
    } else it
}

fun errno(code: Int): String = when (code) {
    -1 -> "[TL] Timeout exceeded"
    1 -> "[EPERM] Operation not permitted"
    2 -> "[ENOENT] No such file or directory"
    3 -> "[ESRCH] No such process"
    4 -> "[EINTR] Interrupted system call"
    5 -> "[EIO] I/O error"
    6 -> "[ENXIO] No such device or address"
    7 -> "[E2BIG] Arg list too long"
    8 -> "[ENOEXEC] Exec format error"
    9 -> "[EBADF] Bad file number"
    10 -> "[ECHILD] No child processes"
    11 -> "[EAGAIN] Try again"
    12 -> "[ENOMEM] Out of memory"
    13 -> "[EACCES] Permission denied"
    14 -> "[EFAULT] Bad address"
    15 -> "[ENOTBLK] Block device required"
    16 -> "[EBUSY] Device or resource busy"
    17 -> "[EEXIST] File exists"
    18 -> "[EXDEV] Cross-device link"
    19 -> "[ENODEV] No such device"
    20 -> "[ENOTDIR] Not a directory"
    21 -> "[EISDIR] Is a directory"
    22 -> "[EINVAL] Invalid argument"
    23 -> "[ENFILE] File table overflow"
    24 -> "[EMFILE] Too many open files"
    25 -> "[ENOTTY] Not a typewriter"
    26 -> "[ETXTBSY] Text file busy"
    27 -> "[EFBIG] File too large"
    28 -> "[ENOSPC] No space left on device"
    29 -> "[ESPIPE] Illegal seek"
    30 -> "[EROFS] Read-only file system"
    31 -> "[EMLINK] Too many links"
    32 -> "[EPIPE] Broken pipe"
    33 -> "[EDOM] Math argument out of domain of func"
    34 -> "[ERANGE] Math result not representable"
    35 -> "[EDEADLK] Resource deadlock would occur"
    36 -> "[ENAMETOOLONG] File name too long"
    37 -> "[ENOLCK] No record locks available"
    38 -> "[ENOSYS] Function not implemented"
    39 -> "[ENOTEMPTY] Directory not empty"
    40 -> "[ELOOP] Too many symbolic links encountered"
    41 -> "[EWOULDBLOCK] Operation would block"
    42 -> "[ENOMSG] No message of desired type"
    43 -> "[EIDRM] Identifier removed"
    44 -> "[ECHRNG] Channel number out of range"
    45 -> "[EL2NSYNC] Level 2 not synchronized"
    46 -> "[EL3HLT] Level 3 halted"
    47 -> "[EL3RST] Level 3 reset"
    48 -> "[ELNRNG] Link number out of range"
    49 -> "[EUNATCH] Protocol driver not attached"
    50 -> "[ENOCSI] No CSI structure available"
    51 -> "[EL2HLT] Level 2 halted"
    52 -> "[EBADE] Invalid exchange"
    53 -> "[EBADR] Invalid request descriptor"
    54 -> "[EXFULL] Exchange full"
    55 -> "[ENOANO] No anode"
    56 -> "[EBADRQC] Invalid request code"
    57 -> "[EBADSLT] Invalid slot"
    58 -> "[EDEADLOCK] File locking deadlock error"
    59 -> "[EBFONT] Bad font file format"
    60 -> "[ENOSTR] Device not a stream"
    61 -> "[ENODATA] No data available"
    62 -> "[ETIME] Timer expired"
    63 -> "[ENOSR] Out of streams resources"
    64 -> "[ENONET] Machine is not on the network"
    65 -> "[ENOPKG] Package not installed"
    66 -> "[EREMOTE] Object is remote"
    67 -> "[ENOLINK] Link has been severed"
    68 -> "[EADV] Advertise error"
    69 -> "[ESRMNT] Srmount error"
    70 -> "[ECOMM] Communication error on send"
    71 -> "[EPROTO] Protocol error"
    72 -> "[EMULTIHOP] Multihop attempted"
    73 -> "[EDOTDOT] RFS specific error"
    74 -> "[EBADMSG] Not a data message"
    75 -> "[EOVERFLOW] Value too large for defined data type"
    76 -> "[ENOTUNIQ] Name not unique on network"
    77 -> "[EBADFD] File descriptor in bad state"
    78 -> "[EREMCHG] Remote address changed"
    79 -> "[ELIBACC] Can not access a needed shared library"
    80 -> "[ELIBBAD] Accessing a corrupted shared library"
    81 -> "[ELIBSCN] .lib section in a.out corrupted"
    82 -> "[ELIBMAX] Attempting to link in too many shared libraries"
    83 -> "[ELIBEXEC] Cannot exec a shared library directly"
    84 -> "[EILSEQ] Illegal byte sequence"
    85 -> "[ERESTART] Interrupted system call should be restarted"
    86 -> "[ESTRPIPE] Streams pipe error"
    87 -> "[EUSERS] Too many users"
    88 -> "[ENOTSOCK] Socket operation on non-socket"
    89 -> "[EDESTADDRREQ] Destination address required"
    90 -> "[EMSGSIZE] Message too long"
    91 -> "[EPROTOTYPE] Protocol wrong type for socket"
    92 -> "[ENOPROTOOPT] Protocol not available"
    93 -> "[EPROTONOSUPPORT] Protocol not supported"
    94 -> "[ESOCKTNOSUPPORT] Socket type not supported"
    95 -> "[EOPNOTSUPP] Operation not supported on transport endpoint"
    96 -> "[EPFNOSUPPORT] Protocol family not supported"
    97 -> "[EAFNOSUPPORT] Address family not supported by protocol"
    98 -> "[EADDRINUSE] Address already in use"
    99 -> "[EADDRNOTAVAIL] Cannot assign requested address"
    100 -> "[ENETDOWN] Network is down"
    101 -> "[ENETUNREACH] Network is unreachable"
    102 -> "[ENETRESET] Network dropped connection because of reset"
    103 -> "[ECONNABORTED] Software caused connection abort"
    104 -> "[ECONNRESET] Connection reset by peer"
    105 -> "[ENOBUFS] No buffer space available"
    106 -> "[EISCONN] Transport endpoint is already connected"
    107 -> "[ENOTCONN] Transport endpoint is not connected"
    108 -> "[ESHUTDOWN] Cannot send after transport endpoint shutdown"
    109 -> "[ETOOMANYREFS] Too many references: cannot splice"
    110 -> "[ETIMEDOUT] Connection timed out"
    111 -> "[ECONNREFUSED] Connection refused"
    112 -> "[EHOSTDOWN] Host is down"
    113 -> "[EHOSTUNREACH] No route to host"
    114 -> "[EALREADY] Operation already in progress"
    115 -> "[EINPROGRESS] Operation now in progress"
    116 -> "[ESTALE] Stale NFS file handle"
    117 -> "[EUCLEAN] Structure needs cleaning"
    118 -> "[ENOTNAM] Not a XENIX named type file"
    119 -> "[ENAVAIL] No XENIX semaphores available"
    120 -> "[EISNAM] Is a named type file"
    121 -> "[EREMOTEIO] Remote I/O error"

    126 -> "Command invoked cannot execute. Missing execute permissions"
    127 -> "Command not found. Command does not exit in the PATH or wrong command name"
    128 -> "Invalid argument to exit"

    129 -> "Terminated with SIGHUP"
    130 -> "Terminated with SIGINT"
    131 -> "Terminated with SIGQUIT"
    132 -> "Terminated with SIGILL"
    133 -> "Terminated with SIGTRAP"
    134 -> "Terminated with SIGABRT"
    135 -> "Terminated with SIGBUS"
    136 -> "Terminated with SIGFPE"
    137 -> "Terminated with SIGKILL"
    138 -> "Terminated with SIGUSR1"
    139 -> "Terminated with SIGSEGV"
    140 -> "Terminated with SIGUSR2"
    141 -> "Terminated with SIGPIPE"
    142 -> "Terminated with SIGALRM"
    143 -> "Terminated with SIGTERM"
    145 -> "Terminated with SIGCHLD"
    146 -> "Terminated with SIGCONT"
    148 -> "Terminated with SIGSTOP"
    149 -> "Terminated with SIGTSTP"
    150 -> "Terminated with SIGTTIN"
    151 -> "Terminated with SIGTTOU"
    152 -> "Terminated with SIGURG"
    153 -> "Terminated with SIGXCPU"
    154 -> "Terminated with SIGXFSZ"
    155 -> "Terminated with SIGVTALRM"
    156 -> "Terminated with SIGPROF"
    157 -> "Terminated with SIGWINCH"
    158 -> "Terminated with SIGIO"
    159 -> "Terminated with SIGPWR"
    160 -> "Terminated with SIGSYS"

    else -> "[whatever it means]"
}
