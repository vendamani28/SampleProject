import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EmployeeService } from '../../../app/service/employee.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-addnew-employee',
  templateUrl: './addnewemployee.component.html',
  styleUrls: ['./addnewemployee.component.css']
})
export class AddnewEmployeeComponent implements OnInit {

  public employeeForm!: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public readonly data: any,
    public readonly dialogRef: MatDialogRef<AddnewEmployeeComponent>,
    private readonly formBuilder: FormBuilder,
    private readonly employeeService: EmployeeService,
    private readonly toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.employeeForm = this.formBuilder.group({
      empId: ['', Validators.required],
      userName: ['', Validators.required],
      email: ['', [Validators.email, Validators.required]],
      jobTitle: ['', Validators.required]
    });
  }

  onSubmit(form: FormGroup) {
    if (this.employeeForm.valid) {
      const employee = {
        empId: form.value['empId'],
        userName: form.value['userName'],
        email: form.value['email'],
        jobTitle: form.value['jobTitle']
      }
      
      this.employeeService.addEmployee(employee).subscribe((res: any) => {
        if (res.status == 'OK') {
          this.toastr.success(res.message);
          this.dialogRef.close(res);
        }
        if (res.status == 'error') {
          this.toastr.error(res.message);
        }
      });
    }
  }

  cancelFuc(): void {
    this.dialogRef.close();
  }

  AddCancelFun(): void {
    this.dialogRef.close();
  }
}
