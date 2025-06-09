import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(public readonly http: HttpClient) { }

  getAllEmployees() {
    const url = 'http://localhost:8081/api/getAllEmployees';
    return this.http.get(url).pipe(map(res => res));
  }

  addEmployee(postObj: any) {
    const URL = 'http://localhost:8081/api/addEmployee';
    return this.http.post(URL, postObj).pipe(map(res => res));
  }

  // getAllExport() {
  //   const URL = 'http://localhost:8081/api/getAllExport';
  //   return this.http.get(URL).pipe(map(res => res));
  // }

  getAllExport() {
    const URL = 'http://localhost:8081/api/getAllExport';
    return this.http.get(URL, { responseType: 'blob' }).pipe(map(res => res));
  }
  // return this.http.get(URL,  { responseType: 'blob' as 'json' }).pipe(map(res => res));

  uploadEmployeeFile(files: any) {
    const URL = 'http://localhost:8081/api/uploadEmployee';
    return this.http.post(URL, files, {
      responseType: 'blob' as 'json',
    }).pipe(map((res: any) => {
      const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      return blob;
    }));
  }

  deletionAll(id: any) {
    const URL = 'http://localhost:8081/api/deleteEmployees/' + id;
    return this.http.delete(URL, { responseType: 'json' }).pipe(map(res => res));
  }

}
