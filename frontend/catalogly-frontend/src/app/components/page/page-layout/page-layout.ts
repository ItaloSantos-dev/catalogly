import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { PageHeader } from '../page-header/page-header';
import { PageFooter } from '../page-footer/page-footer';

@Component({
  selector: 'app-page-layout',
  imports: [RouterOutlet, PageHeader, PageFooter],
  templateUrl: './page-layout.html',
  styleUrl: './page-layout.css',
})
export class PageLayout {

}
