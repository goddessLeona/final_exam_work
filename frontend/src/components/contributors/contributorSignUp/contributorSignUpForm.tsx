"use client";

import { useState } from "react";

import { signUpContributor } from "@/lib/api/contributors/contributorSignUp";
import { Inter, Finger_Paint } from "next/font/google"
import styles from "./contributorSignUpForm.module.css"

const initialFormState = {
    username: "",
    password: "",
    confirmPassword: "",
    email: "",
    firstName: "",
    lastName: "",
};

const inter = Inter({
        subsets: ["latin"],
        weight: ["400"]
    });

const fingerPaint = Finger_Paint({
    subsets: ["latin"],
    weight: "400",
});  

function ContributorSignUpForm() {

    const [formData, setFormData] = useState(initialFormState);
    const [error, setError] = useState<Record<string, string>>({});
    const [loading, setLoading] = useState(false);
    const [success, setSuccess] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setLoading(true);
        setSuccess("");
        setError({});

        if (formData.password !== formData.confirmPassword) {
            setError({confirmPassword: "Password do not match"});
            setLoading(false);
            return;
        }

        try {
            await signUpContributor(formData);

            setSuccess("Signup was successful!");
            setFormData(initialFormState);
            setError({});

        }catch (err: any) {

        
            // field validaion errors
            if (err.errors) {
                setError(err.errors);
                return;
            }

            
            setError({ general: err.message || "Somthing went wrong" });

        } finally {
            setLoading(false);
        }

    };

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement>
        ) => {
        const { name, value } = e.target;

        setFormData((prev) => ({
        ...prev,
            [name] : value,
        }));

        setError((prev) => {
            const newError = { ...prev };
            delete newError[name];
            delete newError.general;
            return newError;
        });

    };

    return (

        <main className={styles.container}>

            <div>
                <p className={styles.thanks}>
                    Happy that you decieded to become a contributor and help the project grow. <br></br>
                    This it the first stepp, after you fil in this form you will get access to your content-page. 
                    From where you can upload content and take part in the comunity.
                </p>
            </div>

            <div className={styles.contentWrapper}>

                <img src="bird4.png" className={styles.sideImage}/>

                <div className={styles.formWrapper}>
        
                    <form onSubmit ={handleSubmit}>
                        <div className={styles.formBox}>

                            <p className={fingerPaint.className}> 
                                SignUp Contributor
                            </p>

                            <div className={styles.field}>
                                <label htmlFor="username">Username</label>
                                <input
                                    id="username"
                                    name="username"
                                    autoComplete="off"
                                    type="text"
                                    value={formData.username}
                                    onChange={handleChange}
                                    required
                                />
                                {error.username && (<p className={styles.error}>{error.username}</p>)}
                            </div>

                            <div className={styles.field}>
                                <label htmlFor="password">Password</label>
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    value={formData.password}
                                    onChange={handleChange}
                                    required
                                />
                                {error.password && (<p className={styles.error}>{error.password}</p>)}
                            </div>

                            <div className={styles.field}>
                                <label htmlFor="confirmPassword">Confirm password</label>
                                <input
                                    id="confirmPassword"
                                    name="confirmPassword"
                                    type="password"
                                    value={formData.confirmPassword}
                                    onChange={handleChange}
                                    required
                                />
                                {error.confirmPassword && (<p className={styles.error}>{error.confirmPassword}</p>)}
                            </div>

                            <div className={styles.field}>
                                <label htmlFor="email">Email</label>
                                <input
                                    id="email"
                                    name="email"
                                    autoComplete="off"
                                    type="text"
                                    value={formData.email}
                                    onChange={handleChange}
                                    required
                                />
                                {error.email && (<p className={styles.error}>{error.email}</p>)}
                            </div>

                            <div className={styles.field}>
                                <label htmlFor="firstName">First name</label>
                                <input
                                    id="firstName"
                                    name="firstName"
                                    autoComplete="off"
                                    type="text"
                                    value={formData.firstName}
                                    onChange={handleChange}
                                    required
                                />
                                {error.firstName && (<p className={styles.error}>{error.firstName}</p>)}
                            </div>

                            <div className={styles.field}>
                                <label htmlFor="lastName">Last name</label>
                                <input
                                    id="lastName"
                                    name="lastName"
                                    autoComplete="off"
                                    type="text"
                                    value={formData.lastName}
                                    onChange={handleChange}
                                    required
                                />
                                {error.lastName && (<p className={styles.error}>{error.lastName}</p>)}
                            </div>
                        
                        <button 
                            className = {styles.btn} 
                            type= "submit"
                            disabled={loading || Object.keys(error).length > 0}
                        >
                            Submit
                        </button>

                        {success && <p className={styles.success}>{success}</p>}
                        {error.general && <p className={styles.error}>{error.general}</p>}

                        </div>

                    </form>
                </div>  
                <img src="bird4.png" className={styles.sideImageRight} />
            </div>    
        </main>
    )
}
export default ContributorSignUpForm;