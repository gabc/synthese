#include "LibHandler.h"

LibHandler::LibHandler(QString name) {
	this->name = name;
	this->id = this->getNextId();
	this->handler = this->getHandler(name);
	this->constructor = this->getConstructor();
}

HMODULE
LibHandler::getHandler(QString name) {
	LPCTSTR fp;
	HMODULE module;

	name = name.append(".dll");
	name[0] = name[0].toUpper();

	fp = (LPCTSTR)name.constData();

	module = LoadLibrary(fp);
	if (!module) {
		qDebug() << __FILE__ << ":" << __LINE__ << GetLastError() << " can't load lib";
		return NULL;
	} else {
		qDebug() << "Loaded lib correctly.";
		return module;
	}
}

FARPROC
LibHandler::getConstructor(void) {
	FARPROC fp;

	fp = GetProcAdress(this->handler, "construct");

	if (!fp) {
		qDebug() << __FILE__ << ":" << __LINE___ << GetLastError() << " can't find proc";
		return NULL;
	} else {
		qDebug() << "Found constructor";
		return fp;
	}
}

int
getNextId(void)
{
	return glbid++;
}
