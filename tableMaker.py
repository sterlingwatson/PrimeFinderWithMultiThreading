import collections
import statistics

RunData = collections.namedtuple('RunData', ['time', 'run'])

results = collections.defaultdict(list)

with open('RunTimes.txt', 'r') as f:
    for line in f:
        # split the line into its components
        #print(f'{line}')
        #ignore empty lines
        if len(line.split()) == 0:
            continue
        _time, _run, _processor, _thread = line.strip().split(',')
        
        time = float(_time.split()[1])
        run = int(_run.split()[1])
        processor = int(_processor.split()[0])
        thread = int(_thread.split()[0])
        
        # add the run data to the corresponding processor-thread group
        results[(processor, thread)].append(RunData(time=time, run=run))

# print the results for each processor-thread group
with open('table.txt', 'w') as out:
    for key, data in results.items():
        processor, thread = key
        out.write(f'Processor  Threads\tRun\tTime\n')
        times = [run.time for run in data]
        for run in data:
            out.write(f'   {processor}\t\t\t {thread}\t\t\t{run.run}\t\t{run.time:.2f}\n')
        out.write(f'Mean:\t\t\t\t\t\t\t\t{statistics.mean(times):.2f}\n')
        out.write(f'Median:\t\t\t\t\t\t\t{statistics.median(times):.2f}\n\n')
