#ifndef DATA_H
#define DATA_H

/*
 * En gros, comme ça, en théorie, ça devrait etre moins la merde.
 * Enfin.. moins..
 */
#ifdef _WIN32
#include <Windows.h>

#define LibGetError GetLastError
typedef FARPROC func;
typedef HMODULE mod;
#else
typedef int(*func)();
#include <dlfcn.h>
typedef void* mod;
#define LibGetError dlerror
#endif



#endif // DATA_H
