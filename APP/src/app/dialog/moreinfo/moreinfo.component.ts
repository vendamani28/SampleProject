import { formatDate } from '@angular/common';
import { Component, Inject, inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { EmployeeService } from 'src/app/service/employee.service';

@Component({
  selector: 'app-moreinfo',
  templateUrl: './moreinfo.component.html',
  styleUrls: ['./moreinfo.component.css']
})
export class MoreinfoComponent {

  selectedfile: any;
  @ViewChild('myInput') myInputVariable: any;
  file: any;

  constructor(public readonly dialogRef: MatDialogRef<MoreinfoComponent>,
    @Inject(MAT_DIALOG_DATA) public readonly data: any,
    private readonly service: EmployeeService,
    private readonly toaster: ToastrService) { }


  //  onChange(event: any) {
  //   //  if(event.target.files.length > 0){
  //   //     this.selectedfile = event.target.files[0];
  //   //  }
  // }

  onChange(event: any) {
    console.log(event);
    this.selectedfile = event.target.files[0];
    const fileName = this.selectedfile.name.toLowerCase();
    if (fileName.endsWith('.xls') || fileName.endsWith('.xlsx')) {
      this.file = this.selectedfile;
    } else {
      this.toaster.error('Please select a valid Excel file');
      this.reset();
    }
  }

  uploadFile() {
    if (this.file) {
      let formData = new FormData();
      formData.append('file', this.file);
      this.service.uploadEmployeeFile(formData).subscribe(res => {
        this.toaster.success("successfully uploaded");
      });
    }
    else {
      this.toaster.error("failed to upload");
    }
  }

  reset() {
    this.myInputVariable.nativeElement.value = "";
  }

  Cancel(): void {
    this.dialogRef.close();
  }
}
