#-------------------------------------------------
#
# Project created by QtCreator 2015-03-13T10:47:51
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = CuteCreatures
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    LibHandler.cpp \
    Creature.cpp

HEADERS  += mainwindow.h \
    LibHandler.h \
    data.h \
    Creature.h

FORMS    += mainwindow.ui

unix:!macx: LIBS += -L$$PWD/../../../../../../../../../lib64/ -ldl

INCLUDEPATH += $$PWD/../../../../../../../../../lib64
DEPENDPATH += $$PWD/../../../../../../../../../lib64
