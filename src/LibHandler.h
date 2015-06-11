#include <QString.h>
#include <Windows.h>
#include <qDebug.h>

static int glbid = 0;

int getNextId();

class LibHandler {
public:
	LibHandler();
	LibHandler(QString name);
	~LibHandler();

	HMODULE getHandler(QString name);
	FARPROC getConstructor();
	
private:
	int id;
	QString name;
	HMODULE handler;
	FARPROC constructor;
};
