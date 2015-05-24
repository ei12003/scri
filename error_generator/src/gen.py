import random, time, string, time

f = open('output' + time.strftime("%Y%M%d%H%M%S"),'a+')

for i in xrange(201):
      error = random.randint(0,1000)
      error_aux = random.randint(0,2)
      error_aux_aux = random.randint(2, 4)
      
      
      value1 = random.uniform(0, 5)
      value2 = random.uniform(0, 5)
      
      sensor1 = '%04.4f' % value1
      sensor2 = '%04.4f' % value2

     
      
      if i != 0:
	# SX_FAILURE
	if error < 200:
	  if error_aux == 0:
	    sensor1 = "--"
	  elif error_aux == 1:
	    sensor2 = "--"
	  else:
	    sensor1 = "--"
	    sensor2 = "--"
	    
	# SX_STUCKAT
	elif error < 300:
	  if error_aux == 0:
	    sensor1 = lastsensor1  
	  elif error_aux == 1:
	    sensor2 = lastsensor2
	  elif error_aux == 2:
	    sensor1 = lastsensor1
	    sensor2 = lastsensor2
	
      # SX_OUT_OF_RANGE
	elif error > 800 and error < 900:
	  if error_aux == 0:
	    sensor1 = str('%04.4f' % random.uniform(0, 1))
	  elif error_aux == 1:
	    sensor2 = str('%04.4f' % random.uniform(0, 1))
	  else:
	    sensor1 = str('%04.4f' % random.uniform(0, 1))
	    sensor2 = str('%04.4f' % random.uniform(0, 1))
	
	# SX_RANDOMVALUES
	elif error > 900:
	  
	  value1 = lastval1+error_aux_aux
	  value2 = lastval2+error_aux_aux
	  
	  if error_aux == 0:
	    sensor1 = '%04.4f' % value1
	  elif error_aux == 1:
	    sensor2 = '%04.4f' % value2
	  else:
	    sensor1 = '%04.4f' % value1
	    sensor2 = '%04.4f' % value2

      string = sensor1 + " " + sensor2 + "\n"
      f.write(string)
      
      lastsensor1 = sensor1
      lastsensor2 = sensor2
      
      lastval1 = value1
      lastval2 = value2
      
      #print error,error_aux,error_aux_aux
      #print string
      
      
    
      
      