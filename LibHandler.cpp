#include "LibHandler.h"

void
formatName(QString *str)
{
#ifdef _WIN32
	str->append(".dll");
#else
	str->append(".so");
	str->prepend("./");
#endif
}

LibHandler::LibHandler(QString *name) {
    this->name = *name;
    this->id = this->getNextId();
    this->handler = this->getHandler(name);
    this->constructor = this->getConstructor();
}

mod
LibHandler::getHandler(QString *name) {
    mod module;
    LPCTSTR fp;

	formatName(name);

    fp = (LPCTSTR)name->toStdString().c_str();

#ifdef _WIN32
	module = LoadLibrary(fp);
#else
	LibGetError(); // This needs to happend first.
    module = dlopen(fp, RTLD_LAZY);
#endif

    if (!module) {
		qDebug() << __FILE__ << ":" << __LINE__ << LibGetError() << " can't load lib";
        return NULL;
    } else {
        qDebug() << "Loaded lib correctly.";
        return module;
    }
}

func
LibHandler::getConstructor(void) {
    func fp;

#ifdef _WIN32
	fp = GetProcAddress(this->handler, "construct");
#else
    dlerror(); // This need to happend first.
    fp = (func)dlsym(this->handler, "construct");
#endif

    if (!fp) {
		qDebug() << __FILE__ << ":" << __LINE__ << LibGetError() << " can't find proc";
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
