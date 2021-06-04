#!/usr/bin/env python
# coding: utf-8

# In[1]:


import pandas as pd
import json
from habanero import Crossref
cr = Crossref()


# In[2]:


# query
x = cr.works(query = "Covid American Latin", limit= 1000)
x['message']
x['message']['total-results']
x['message']['items']

# fetch data by DOI
#cr.works(ids = [ '10.31744/einstein_journal/2021ao6282'  ])


# In[3]:


with open('personal.json', 'w') as json_file:
    json.dump(x, json_file)


# In[ ]:




