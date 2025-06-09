import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';

import { EmployeeService } from '../../../app/service/employee.service';
import { MoreinfoComponent } from '../../../app/dialog/moreinfo/moreinfo.component';
import { AddnewEmployeeComponent } from '../../../app/dialog/addnewemployee/addnewemployee.component';

@Component({
  selector: 'app-userlist',
  templateUrl: './userlist.component.html',
  styleUrls: ['./userlist.component.css']
})
export class UserlistComponent implements OnInit {
  headerCount: number | undefined;
  items: any
  id: any;
  getAllEmployees: any;


  constructor(public dialog: MatDialog,
    private readonly service: EmployeeService,
    private readonly toastr: ToastrService) { }

  ngOnInit(): void {
    this.getAllEmployeesList();
  }

  getAllEmployeesList() {
    this.service.getAllEmployees().subscribe((res: any) => {
      this.items = res;
    });
  }

  addnewEmployee(type: any, info: any): void {
    const dialogRef = this.dialog.open(AddnewEmployeeComponent, {
      width: '850px',
      height: '300px',
      data: { type, info }
    });

    dialogRef.afterClosed().subscribe(() => {
      this.getAllEmployeesList();
    });
  }

  uploadFile(): void {
    const dialogRef = this.dialog.open(MoreinfoComponent, {
      width: '900px',
      height: '250px'
    });

    dialogRef.afterClosed().subscribe(data => {
      this.getAllEmployeesList();
      console.log(data);
    })
  }

  exportFile() {
    this.service.getAllExport().subscribe((response: any) => {
      const blob = new Blob([response], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.target = '_blank';
      a.download = 'employees.xlsx'; // Set the file name
      a.click();
      window.URL.revokeObjectURL(url); // Revoke the object URL after download
    });
  }

  deleteFuc(id: any) {
    this.service.deletionAll(id).subscribe((res: any) => {
      if (res.status === 'SUCCESS') {
        this.toastr.success(res.message);
        this.getAllEmployeesList();
      } else {
        this.toastr.error(res.message);
      }
    });
  }
}
