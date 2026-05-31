import { Routes } from '@angular/router';
import { PageLayout } from './components/page/page-layout/page-layout';
import { PageHome } from './components/page/page-home/page-home';

export const routes: Routes = [
    {
        path: '',
        component:PageLayout,
        children: [
            {
                path:'',
                component: PageHome
            }
        ]
    }
];
