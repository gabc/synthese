#include "LibHandler.h"


LibHandler::LibHandler(QString *name) {
    this->name = *name;
    this->id = this->getNextId();
    this->handler = this->getHandler(name);
    this->constructor = this->getConstructor();
}

void *
LibHandler::getHandler(QString *name) {
    void *module;
    char *fp;

    name->append(".so");
    name->prepend("./");

    fp = (char *)name->toStdString().c_str();

    dlerror(); // This needs to happend first.
    module = dlopen(fp, RTLD_LAZY);
    if (!module) {
        qDebug() << __FILE__ << ":" << __LINE__ << dlerror() << " can't load lib";
        return NULL;
    } else {
        qDebug() << "Loaded lib correctly.";
        return module;
    }
}

func
LibHandler::getConstructor(void) {
    func fp;

    dlerror(); // This need to happend first.
    fp = (func)dlsym(this->handler, "construct");

    if (!fp) {
        qDebug() << __FILE__ << ":" << __LINE__ << dlerror() << " can't find proc";
        return NULL;
    } else {
        qDebug() << "Found constructor";
        return fp;
    }
}

int
LibHandler::getNextId(void)
{
    return glbid++;
}
