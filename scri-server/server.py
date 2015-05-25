from bottle import run, get, post, request # or route



files = []
WAIT = "WAIT"
FAIL = "FAIL"

@post('/scri') # or @route('/login', method='POST')
def addFile():
    data = request.forms.get('data').split('\n')
    
    if(data[len(data)-1]==''):
      data = data[:-1]
    
    files.append(data);
    if len(files) == 3:
      vote()
    print len(files)
    return ""



def vote():
  f0 = f1 = f2 = 1
  global files
  max = len(files[0])
  
  if max < len(files[1]):
    max = len(files[1])
    
  if max < len(files[2]):
    max = len(files[2])
  
  votes = []  
  for i in range(0,max):

      files_ = [];
      
      
      if i < len(files[0]) and f0:
	if files[0][i] != FAIL:
	  files_.append(files[0][i])
	else:
	  f0 = 0
	       
      if (i < len(files[1])) and f1 :
	if files[1][i] != FAIL:
	  files_.append(files[1][i])
	else:
	  f1 = 0
      
      if i < len(files[2]) and f2:
	if files[2][i] != FAIL:
	  files_.append(files[2][i])
	else:
	  f2 = 0
	       
      value = decide(files_)
      
      if value == -1:
	break
      
      votes.append(str(value)+"\n")

  votes.append("END")
  print "End"
  with open("final_decision", 'w') as file_:
            for vote in votes:
                file_.write(vote)
  
    
  files = []
     

  
def decide(values):
  if len(values) == 3:
    value = majority(values)
    if value == -1:
      value = mean(values)
      if value == -1:
	return -1
    return value
    
  elif len(values) == 2:
    value = mean(values)
    if value == -1:
	return -1
    return value
  
  else:
    return -1;
  
  
def majority(values):
  check = values.count(values[0])
  check2 = values.count(values[1])
  if check > 1:
    return values[0]
  elif check2 > 1:
    return values[1]
  else:
    return -1
  
def mean(values):
  sum = 0
  counter = 0
  for value in values:
    if value != WAIT:
      sum += int(value)
      counter += 1    
  if counter > 1:
    return sum/counter
  
  return -1



run(host='localhost', port=8080, debug=True, reloader=True)


