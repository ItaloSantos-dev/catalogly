import { Routes } from '@angular/router';
import { PageLayout } from './components/page/page-layout/page-layout';
import { PageHome } from './components/page/page-home/page-home';
import { RegisterSeller } from './components/auth/seller/register-seller/register-seller';

export const routes: Routes = [
    {
        path: '',
        component:PageLayout,
        children: [
            {
                path:'',
                component: PageHome
            },
            {
                path:'seller/register',
                component: RegisterSeller
            }
        ]
    },
    
    
];
