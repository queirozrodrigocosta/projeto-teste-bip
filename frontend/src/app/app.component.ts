import { Component } from '@angular/core';
import { BeneficiarioComponent } from './beneficiario.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [BeneficiarioComponent],
  template: `<app-beneficiario></app-beneficiario>`,
  styles: []
})
export class AppComponent {
  title = 'BIP Frontend';
}