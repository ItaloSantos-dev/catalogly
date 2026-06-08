import { Routes } from '@angular/router';
import { PageLayout } from './components/page/page-layout/page-layout';
import { PageHome } from './components/page/page-home/page-home';
import { RegisterSeller } from './components/auth/seller/register-seller/register-seller';
import { CatalogLayout } from './components/catalog/seller-side/catalog-layout/catalog-layout';
import { CatalogDashboard } from './components/catalog/seller-side/catalog-dashboard/catalog-dashboard';
import { LoginSeller } from './components/auth/seller/login-seller/login-seller';
import { CatalogSellerSideItem } from './components/catalog/seller-side/catalog-seller-side-item/catalog-seller-side-item';

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
            },
            {
                path:'seller/login',
                component: LoginSeller
            },
        ]
    },
    {
        path: 'catalog',
        component: CatalogLayout,
        children: [
            {
                path: 'dashboard',
                component: CatalogDashboard
            },
            {
                path: 'products',
                component:CatalogSellerSideItem
            }
        ]
    }
    
];
