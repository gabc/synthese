#include "mainwindow.h"
#include <QApplication>


#include "LibHandler.h"

typedef int (*func)();

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();
    QString *s = new QString("foo");
    LibHandler *lh = new LibHandler(s);

    func f = lh->getConstructor();
    qDebug() << f();
    return a.exec();
}
