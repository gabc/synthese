#ifndef LIBHANDLER_H
#define LIBHANDLER_H

#include <QString>
#include <QDebug>
#include <dlfcn.h>

// Windows thing. Need to make two separate files.
//#include <Windows.h>

typedef int (*func)();

static int glbid = 0;

class LibHandler {
public:
    LibHandler();
    LibHandler(QString *name);
    ~LibHandler();

    void *getHandler(QString *name);
    func getConstructor();
    int getNextId();

private:
    int id;
    QString name;
    void *handler;
    func constructor;
};

# endif
