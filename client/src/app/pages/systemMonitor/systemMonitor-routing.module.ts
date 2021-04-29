import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SystemMonitorComponent } from './systemMonitor.component';

const routes: Routes = [
  { path: '', component: SystemMonitorComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SystemMonitorRoutingModule { }
