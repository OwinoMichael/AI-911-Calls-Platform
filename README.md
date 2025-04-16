
# 🚨 911 Calls Analysis & Visualization Platform

This project is a **Jakarta EE-based Spring Boot web application** that integrates with **Jupyter Notebooks** to analyze and visualize 911 call data. It generates valuable insights, features, and metrics that can be leveraged by city or municipal governments to support **data-driven decisions**.

---

## 📊 Project Overview

The application allows different users (Admin, Developer, General) to:
- Run analysis notebooks asynchronously using Papermill.
- View and track job statuses (`PENDING`, `COMPLETED`, etc.).
- View interactive dashboards for each user role.
- Extract features and metrics from 911 call data for policy-making, resource allocation, and emergency response optimization.

---

## 🧱 Technology Stack

- **Backend**: Spring Boot (Jakarta EE, Spring MVC, Spring Data JPA)
- **Frontend**: Thymeleaf + HTML templates
- **Python Integration**: Papermill for executing Jupyter notebooks
- **Database**: H2/PostgreSQL (based on your config)
- **Dependency Injection**: Spring Boot annotations (e.g., `@Autowired`)
- **ORM**: JPA/Hibernate
- **Dev Tools**: Lombok, Maven

---

## 🔌 Application Endpoints

### ContentController

```
/login        → Login page  
/signup       → Signup page  
/index        → Home page  
```

### DashboardController

```
/admin/dashboard      → Admin view  
/developer/dashboard  → Developer view  
/general/dashboard    → General user view  
```

### JobController

```
POST   /create                 → Create a new job  
POST   /{jobId}/run           → Run Jupyter notebook for a job  
GET    /{jobId}/results       → View notebook execution results  
GET    /{jobId}               → Get single job details  
GET    /?status={status}      → Filter jobs by status  
```

---

## 📁 Python Notebook Integration

We use a lightweight Python runner (`run_notebook.py`) to execute parameterized notebooks using [Papermill](https://papermill.readthedocs.io/).

### Example: `run_notebook.py`

```python
pm.execute_notebook(
    input_path,
    output_path,
    parameters=dict(job_id=jobId)
)
```

---

## 📦 Python Requirements

Create a `requirements.txt` file in the `src/python/` directory with the following:

```txt
papermill==2.4.0
nbformat
notebook
ipykernel
```

Install with:

```bash
pip install -r requirements.txt
```

---

## 🚀 How to Run

### Backend (Spring Boot)
```bash
./mvnw spring-boot:run
```

### Python Environment

Make sure to activate your virtual environment:
```bash
source venv/bin/activate
```

And ensure the path to `python` is correctly referenced in your Java service (e.g., `NotebookService`):
```java
"/path/to/venv/bin/python"
```

---

## ✅ Sample Output

Once executed, notebook results are persisted and accessible via:
```
GET /{jobId}/results
```

Notebook output will include:
- Data summaries
- Visual plots (heatmaps, bar charts, time series)
- Feature engineering results

---

## 🧠 Use Cases

- Emergency resource allocation
- Policy insight for local governments
- Public safety and response optimization
- Historical trends and heatmap analysis

---

## 🧾 License

MIT License. See `LICENSE` file for more details.
