#ifndef LIBHANDLER_H
#define LIBHANDLER_H

#include <QString>
#include <QDebug>
#include "data.h"

// Windows thing. Need to make two separate files.


static int glbid = 0;

class LibHandler {
public:
    LibHandler();
    LibHandler(QString *name);
    ~LibHandler();

    mod getHandler(QString *name);
    func getConstructor();
    int getNextId();

private:
    int id;
    QString name;
    mod handler;
    func constructor;
};

# endif
