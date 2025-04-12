import sys
import papermill as pm

def main():
    if len(sys.argv) != 4:
        print("Usage: python3 run_notebook.py <input_notebook.ipynb> <output_notebook.ipynb> <job_id>")
        sys.exit(1)

    input_path = sys.argv[1]
    output_path = sys.argv[2]
    jobId = sys.argv[3]
    print(input_path)
    print(output_path)
    try:
        print(f"Running notebook with job_id={jobId}")
        pm.execute_notebook(
            input_path,
            output_path,
            parameters=dict(job_id=jobId)
        )
        print("Notebook executed successfully.")
    except Exception as e:
        print(f"Error during notebook execution: {e}")
        sys.exit(1)

if __name__ == "__main__":
    main()
