# -*- coding: utf-8 -*-
"""
Created on Thu Feb 24 11:37:28 2022

@author: Daniel Nelson
"""

#########################
#Imports
#########################
import os #need this library to change file locations
import sys
import argparse #need argparse library to take inputs from the commandline
import datetime #date time library, need to make a date time variable.
import pandas
import geopandas as gpd  
import matplotlib.pyplot as plt 
import fiona
from mpl_toolkits.axes_grid1 import make_axes_locatable
from PyQt5 import QtWidgets
from PyQQtWidgets import QApplication, QMainWindow
from PySide2.QtCore import *
from PySide2.QtGui import *
from PySide2.QtWidgets import *



def FilterFiles (path):
    #set the chosen directory
    os.chdir(path)
    


    return

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        if not MainWindow.objectName():
            MainWindow.setObjectName(u"MainWindow")
        MainWindow.resize(800, 600)
        self.actionSubs = QAction(MainWindow)
        self.actionSubs.setObjectName(u"actionSubs")
        self.actionSubs.setCheckable(True)
        self.actionSubs.setChecked(False)
        self.actionSubs.setMenuRole(QAction.TextHeuristicRole)
        self.actionAbout = QAction(MainWindow)
        self.actionAbout.setObjectName(u"actionAbout")
        self.actionAbout.setShortcutContext(Qt.WidgetShortcut)
        self.actionAbout.setMenuRole(QAction.NoRole)
        self.actionUsage = QAction(MainWindow)
        self.actionUsage.setObjectName(u"actionUsage")
        self.actionUsage.setMenuRole(QAction.TextHeuristicRole)
        self.actionEnable_Preview = QAction(MainWindow)
        self.actionEnable_Preview.setObjectName(u"actionEnable_Preview")
        self.actionEnable_Preview.setCheckable(True)
        self.centralwidget = QWidget(MainWindow)
        self.centralwidget.setObjectName(u"centralwidget")
        self.RefreshFiles = QPushButton(self.centralwidget)
        self.RefreshFiles.setObjectName(u"RefreshFiles")
        self.RefreshFiles.setGeometry(QRect(500, 60, 75, 23))
        self.SelectFolder = QPushButton(self.centralwidget)
        self.SelectFolder.setObjectName(u"SelectFolder")
        self.SelectFolder.setGeometry(QRect(0, 60, 91, 23))
        self.FInfo = QLabel(self.centralwidget)
        self.FInfo.setObjectName(u"FInfo")
        self.FInfo.setGeometry(QRect(650, 60, 81, 16))
        self.FList = QLabel(self.centralwidget)
        self.FList.setObjectName(u"FList")
        self.FList.setGeometry(QRect(270, 60, 47, 13))
        self.scrollArea = QScrollArea(self.centralwidget)
        self.scrollArea.setObjectName(u"scrollArea")
        self.scrollArea.setGeometry(QRect(10, 90, 561, 461))
        self.scrollArea.setWidgetResizable(True)
        self.scrollAreaWidgetContents = QWidget()
        self.scrollAreaWidgetContents.setObjectName(u"scrollAreaWidgetContents")
        self.scrollAreaWidgetContents.setGeometry(QRect(0, 0, 559, 459))
        self.scrollArea.setWidget(self.scrollAreaWidgetContents)
        self.FSize = QLabel(self.centralwidget)
        self.FSize.setObjectName(u"FSize")
        self.FSize.setGeometry(QRect(580, 100, 47, 13))
        self.Date = QLabel(self.centralwidget)
        self.Date.setObjectName(u"Date")
        self.Date.setGeometry(QRect(580, 120, 47, 13))
        self.TopLeft = QLabel(self.centralwidget)
        self.TopLeft.setObjectName(u"TopLeft")
        self.TopLeft.setGeometry(QRect(580, 160, 71, 16))
        self.BottomRight = QLabel(self.centralwidget)
        self.BottomRight.setObjectName(u"BottomRight")
        self.BottomRight.setGeometry(QRect(580, 180, 91, 16))
        self.FType = QLabel(self.centralwidget)
        self.FType.setObjectName(u"FType")
        self.FType.setGeometry(QRect(580, 140, 47, 13))
        self.NShapes = QLabel(self.centralwidget)
        self.NShapes.setObjectName(u"NShapes")
        self.NShapes.setGeometry(QRect(580, 220, 101, 16))
        self.GridSize = QLabel(self.centralwidget)
        self.GridSize.setObjectName(u"GridSize")
        self.GridSize.setGeometry(QRect(580, 240, 81, 16))
        self.FSizeINPUT = QLabel(self.centralwidget)
        self.FSizeINPUT.setObjectName(u"FSizeINPUT")
        self.FSizeINPUT.setGeometry(QRect(700, 100, 47, 13))
        self.DateINPUT = QLabel(self.centralwidget)
        self.DateINPUT.setObjectName(u"DateINPUT")
        self.DateINPUT.setGeometry(QRect(700, 120, 47, 13))
        self.FTypeINPUT = QLabel(self.centralwidget)
        self.FTypeINPUT.setObjectName(u"FTypeINPUT")
        self.FTypeINPUT.setGeometry(QRect(700, 140, 47, 13))
        self.TopLeftINPUT = QLabel(self.centralwidget)
        self.TopLeftINPUT.setObjectName(u"TopLeftINPUT")
        self.TopLeftINPUT.setGeometry(QRect(700, 160, 47, 13))
        self.BottomRightINPUT = QLabel(self.centralwidget)
        self.BottomRightINPUT.setObjectName(u"BottomRightINPUT")
        self.BottomRightINPUT.setGeometry(QRect(700, 180, 47, 13))
        self.NShapesINPUT = QLabel(self.centralwidget)
        self.NShapesINPUT.setObjectName(u"NShapesINPUT")
        self.NShapesINPUT.setGeometry(QRect(700, 220, 47, 13))
        self.GridSizeINPUT = QLabel(self.centralwidget)
        self.GridSizeINPUT.setObjectName(u"GridSizeINPUT")
        self.GridSizeINPUT.setGeometry(QRect(700, 240, 47, 13))
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QMenuBar(MainWindow)
        self.menubar.setObjectName(u"menubar")
        self.menubar.setGeometry(QRect(0, 0, 800, 21))
        self.menuSettings = QMenu(self.menubar)
        self.menuSettings.setObjectName(u"menuSettings")
        self.menuInformation = QMenu(self.menubar)
        self.menuInformation.setObjectName(u"menuInformation")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QStatusBar(MainWindow)
        self.statusbar.setObjectName(u"statusbar")
        self.statusbar.setEnabled(True)
        MainWindow.setStatusBar(self.statusbar)

        self.menubar.addAction(self.menuSettings.menuAction())
        self.menubar.addAction(self.menuInformation.menuAction())
        self.menuSettings.addAction(self.actionSubs)
        self.menuSettings.addAction(self.actionEnable_Preview)
        self.menuInformation.addAction(self.actionAbout)
        self.menuInformation.addAction(self.actionUsage)

        self.retranslateUi(MainWindow)

        QMetaObject.connectSlotsByName(MainWindow)
    # setupUi

    def retranslateUi(self, MainWindow):
        MainWindow.setWindowTitle(QCoreApplication.translate("MainWindow", u"MainWindow", None))
        self.actionSubs.setText(QCoreApplication.translate("MainWindow", u"Scan Subdirectories", None))
#if QT_CONFIG(shortcut)
        self.actionSubs.setShortcut(QCoreApplication.translate("MainWindow", u"Ctrl+D", None))
#endif // QT_CONFIG(shortcut)
        self.actionAbout.setText(QCoreApplication.translate("MainWindow", u"About", None))
        self.actionUsage.setText(QCoreApplication.translate("MainWindow", u"Usage", None))
        self.actionEnable_Preview.setText(QCoreApplication.translate("MainWindow", u"Enable Preview", None))
#if QT_CONFIG(shortcut)
        self.actionEnable_Preview.setShortcut(QCoreApplication.translate("MainWindow", u"Ctrl+P", None))
#endif // QT_CONFIG(shortcut)
        self.RefreshFiles.setText(QCoreApplication.translate("MainWindow", u"Refresh", None))
        self.SelectFolder.setText(QCoreApplication.translate("MainWindow", u"Select Folder", None))
        self.FInfo.setText(QCoreApplication.translate("MainWindow", u"File Information", None))
        self.FList.setText(QCoreApplication.translate("MainWindow", u"File List", None))
        self.FSize.setText(QCoreApplication.translate("MainWindow", u"File Size:", None))
        self.Date.setText(QCoreApplication.translate("MainWindow", u"Date:", None))
        self.TopLeft.setText(QCoreApplication.translate("MainWindow", u"Top Left LOC:", None))
        self.BottomRight.setText(QCoreApplication.translate("MainWindow", u"Bottom Right LOC:", None))
        self.FType.setText(QCoreApplication.translate("MainWindow", u"File Type:", None))
        self.NShapes.setText(QCoreApplication.translate("MainWindow", u"Number of Shapes:", None))
        self.GridSize.setText(QCoreApplication.translate("MainWindow", u"Grid Cell Size:", None))
        self.FSizeINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.DateINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.FTypeINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.TopLeftINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.BottomRightINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.NShapesINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.GridSizeINPUT.setText(QCoreApplication.translate("MainWindow", u"TextLabel", None))
        self.menuSettings.setTitle(QCoreApplication.translate("MainWindow", u"Settings", None))
        self.menuInformation.setTitle(QCoreApplication.translate("MainWindow", u"Information", None))
    # retranslateUi

def main ():
    # start GUI and set input collection
    
    path = args.path #makes a variable called path from the input for the file path
    FilterFiles(path)
    












if __name__ == '__main__':
    #Run the main function
    main()