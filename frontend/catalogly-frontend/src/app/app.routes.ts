import { Routes } from '@angular/router';
import { LandingLayout } from './components/landing-page/layout/landing-layout/landing-layout';
import { LandingHome } from './components/landing-page/home/landing-home/landing-home';
import { SellerRegister } from './components/seller-side/auth/seller-register/seller-register';
import { SellerLogin } from './components/seller-side/auth/seller-login/seller-login';
import { SellerLayout } from './components/seller-side/catalog/layout/seller-layout/seller-layout';
import { SellerDashboard } from './components/seller-side/catalog/seller-dashboard/seller-dashboard';
import { SellerShowItems } from './components/seller-side/catalog/item/seller-show-items/seller-show-items';
import { SellerCreateCatalog } from './components/seller-side/catalog/seller-create-catalog/seller-create-catalog';
import { SellerCreateItem } from './components/seller-side/catalog/item/seller-create-item/seller-create-item';
import { SellerShowCategorys } from './components/seller-side/catalog/category/seller-show-categorys/seller-show-categorys';
import { SellerCreateCategory } from './components/seller-side/catalog/category/seller-create-category/seller-create-category';
import { SellerShowItemsOfCategory } from './components/seller-side/catalog/category/seller-show-items-of-category/seller-show-items-of-category';
import { SellerShowCoupons } from './components/seller-side/catalog/coupon/seller-show-coupons/seller-show-coupons';
import { SellerCreateCoupon } from './components/seller-side/catalog/coupon/seller-create-coupon/seller-create-coupon';
import { SellerShowSuppliers } from './components/seller-side/catalog/supplier/seller-show-suppliers/seller-show-suppliers';
import { SellerCreateSupplier } from './components/seller-side/catalog/supplier/seller-create-supplier/seller-create-supplier';
import { SellerShowItem } from './components/seller-side/catalog/item/seller-show-item/seller-show-item';


export const routes: Routes = [
    {
        path: '',
        component:LandingLayout,
        children: [
            {
                path:'',
                component: LandingHome
            },
            {
                path:'seller/register',
                component: SellerRegister
            },
            {
                path:'seller/login',
                component: SellerLogin
            },
        ]
    },
    {
        path: 'catalog',
        component: SellerLayout,
        children: [
            {
                path: 'create',
                component:SellerCreateCatalog
            },
            {
                path: 'dashboard',
                component: SellerDashboard
            },
            {
                path: 'products',
                component:SellerShowItems,
            },
            {
                path:"products/create",
                component:SellerCreateItem
            },
            {
                path:"products/:id",
                component:SellerShowItem
            },
            {
                path: 'categories',
                component:SellerShowCategorys
            },
            {
                path: 'categories/create',
                component:SellerCreateCategory
            },
            {
                path: 'categories/:id/products',
                component:SellerShowItemsOfCategory
            },
            {
                path: 'coupons',
                component:SellerShowCoupons
            },
            {
                path: 'coupons/create',
                component:SellerCreateCoupon
            },
            {
                path: 'suppliers',
                component:SellerShowSuppliers
            },
            {
                path: 'suppliers/create',
                component:SellerCreateSupplier
            },
 
        ]
    }
    
];
