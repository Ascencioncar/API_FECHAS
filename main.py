import psycopg2
import tkinter as tk
from tkinter import ttk


conn = psycopg2.connect(database = "dbfechas", 
                        user = "postgres", 
                        host= 'localhost',
                        password = "1001561031",
                        port = 5432)

cur = conn.cursor()
cur.execute("SHOW port;")
port = cur.fetchone()[0]
print(f"El puerto usado por PostgreSQL es: {port}")

cur.execute("SELECT current_user;")
user = cur.fetchone()[0]
print(f"El usuario de PostgreSQL es: {user}")

cur.close()
conn.close()